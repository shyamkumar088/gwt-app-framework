/*
 * Copyright 2008. Mount Sinai Hospital, Toronto, Canada.
 * 
 * Licensed under the Apache License, Version 2.0. You
 * can find a copy of the license at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * IN NO EVENT SHALL MOUNT SINAI HOSPITAL BE LIABLE TO ANY PARTY FOR DIRECT, 
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST 
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, 
 * EVEN IF MOUNT SINAI HOSPITAL HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * 
 * MOUNT SINAI HOSPITAL SPECIFICALLY DISCLAIMS ANY IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND 
 * ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". 
 * MOUNT SINAI HOSPITAL HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, 
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS. 
 */
package org.gwtaf.widgets.expanding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gwtaf.eventbus.EventBus;
import org.gwtaf.widgets.Presenter;
import org.gwtaf.widgets.View;
import org.gwtaf.widgets.expanding.event.PresenterCreatedEvent;
import org.gwtaf.widgets.expanding.event.PresenterRemovedEvent;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A concrete implementation of {@link ExpandableTablePresenter}.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <P>
 *            the Presenter that this Presenter will keep track of and fire
 *            events for.
 * @param <V>
 *            the View type that the expandable table will be replicating.
 * @param <M>
 *            the model type.
 */
public class ExpandableTablePresenterImpl<P extends Presenter<V, M>, V extends View, M>
		implements ExpandableTablePresenter<P, V, M> {

	/**
	 * The {@link EventBus} to fire events on.
	 */
	private EventBus eventBus;

	/**
	 * The list of models that are linked to the views in the
	 * {@link ExpandableTable}.
	 */
	private List<M> models = new ArrayList<M>();

	/**
	 * A map of views to the presenters that contain them. This map is only for
	 * convenience sake making removal simpler.
	 */
	private Map<V, P> viewToPresenter = new HashMap<V, P>();

	/**
	 * The {@link ExpandableTable} that acts as the View for this Presenter.
	 */
	private ExpandableTable<V> view;

	/**
	 * A {@link Provider} for the Presenters that this Presenter is replicating
	 * (those Presenter's views are what go into the {@link ExpandableTable}).
	 */
	private Provider<P> presenterProvider;

	/**
	 * A {@link Provider} of {@link PresenterCreatedEvent}s.
	 */
	private Provider<PresenterCreatedEvent<P>> presenterCreatedEventProvider;

	/**
	 * A {@link Provider} of {@link PresenterRemovedEvent}s.
	 */
	private Provider<PresenterRemovedEvent<P>> presenterRemovedEventProvider;

	/**
	 * Creates a new <code>ExpandableTablePresenterImpl</code> with the given
	 * injected parameters.
	 * 
	 * @param eventBus
	 *            the {@link EventBus}.
	 * @param view
	 *            the {@link ExpandableTable} as the view.
	 * @param presenterProvider
	 *            the Provider of Presenters to replicate.
	 * @param presenterCreatedEventProvider
	 *            a provider for {@link PresenterCreatedEvent}s.
	 * @param presenterRemovedEventProvider
	 *            a provider for {@link PresenterRemovedEvent}s.
	 */
	@Inject
	public ExpandableTablePresenterImpl(EventBus eventBus,
			ExpandableTable<V> view, Provider<P> presenterProvider,
			Provider<PresenterCreatedEvent<P>> presenterCreatedEventProvider,
			Provider<PresenterRemovedEvent<P>> presenterRemovedEventProvider) {
		this.eventBus = eventBus;
		this.view = view;
		this.presenterProvider = presenterProvider;
		this.presenterCreatedEventProvider = presenterCreatedEventProvider;
		this.presenterRemovedEventProvider = presenterRemovedEventProvider;

		this.view.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				viewClicked(Element.as(event.getNativeEvent().getEventTarget()));
			}
		});
	}

	public ExpandableTable<V> getView() {
		return view;
	}

	public List<M> getModel() {

		// telling all presenters to fill in their models.
		for (V view : viewToPresenter.keySet()) {

			// we don't care for the return of this since the presenter's model
			// is already in our [models] array.
			viewToPresenter.get(view).getModel();
		}
		return models;
	}

	public void setModel(List<M> dataFromModel) {

		// check parameters.
		assert dataFromModel != null : getClass().getName()
				+ ": cannot set a null list of models.";

		// copy over the arguments
		List<M> argumentCopy = new ArrayList<M>();
		argumentCopy.addAll(dataFromModel);

		// clear everything.
		view.clear();
		viewToPresenter.clear();
		List<M> oldData = models;

		models.clear();

		// do not fire property change event when adding models. We don't want
		// the source (model) to sync with the incomplete models of the ExFT.
		for (M modelToAdd : argumentCopy) {
			addAndFireEvent(modelToAdd);
		}
	}

	/**
	 * Remove the row with the given {@link RemoveButton} and return the View
	 * that was removed.
	 * 
	 * @param removeButton
	 *            the {@link RemoveButton} on the row.
	 * @return the View that was removed.
	 */
	protected V removeRow(RemoveButton removeButton) {

		// check parameter.
		assert removeButton != null : getClass().getName()
				+ ": RemoveButton must be instantiated to be removed.";

		return view.remove(removeButton);
	}

	/**
	 * Creates a new Presenter and adds the View to this Presenter's
	 * {@link ExpandableTable}. A {@link PresenterCreatedEvent} is then fired on
	 * the {@link EventBus}.
	 */
	protected void addAndFireEvent() {
		addAndFireEvent(null);
	}

	/**
	 * Creates a new Presenter and adds the View to this Presenter's
	 * {@link ExpandableTable}. The new Presenter's model is set to
	 * <code>model</code>. A {@link PresenterCreatedEvent} is then fired on the
	 * {@link EventBus}.
	 * 
	 * @param model
	 *            the model to set.
	 * @param fireProps
	 *            flag to fire property changed event or not.
	 */
	protected void addAndFireEvent(M model) {

		// create the presenter and add it's view to the expandable table.
		P presenter = presenterProvider.get();
		V viewToAdd = presenter.getView();
		// viewToAdd.render();
		view.add(viewToAdd);

		// if the given model isn't null, set it in the presenter.
		if (model != null) {
			presenter.setModel(model);
		}

		// store the created model in our list of models.
		models.add(presenter.getModel());

		// add a map for the view to the presenter.
		viewToPresenter.put(presenter.getView(), presenter);

		// now we need to fire the correct event on the event bus.
		PresenterCreatedEvent<P> presenterCreated = presenterCreatedEventProvider
				.get();
		presenterCreated.setPresenter(presenter);
		eventBus.fireEvent(presenterCreated);
	}

	/**
	 * Fires a {@link PresenterRemovedEvent} for the given V view. It also
	 * removes the Presenter from this <code>ExpandableTablePresenterImpl</code>
	 * 
	 * @param view
	 *            the View that was removed from the UI.
	 */
	protected void fireRemoveEvent(V view) {
		P presenter = viewToPresenter.get(view);
		if (presenter != null) {

			// remove the model and view.
			models.remove(presenter.getModel());
			viewToPresenter.remove(view);

			// create a PresenterRemovedEvent and fire it.
			PresenterRemovedEvent<P> presenterRemovedEvent = presenterRemovedEventProvider
					.get();
			presenterRemovedEvent.setPresenter(presenter);
			eventBus.fireEvent(presenterRemovedEvent);
		}
	}

	protected void viewClicked(Element source) {

		// triage the event.
		if (source == view.getAddButton().getContainingWidget().getElement()) {
			addAndFireEvent();
		} else {

			// check all the remove buttons's elements.
			Iterator<RemoveButton> iter = view.getRemoveButtons().iterator();
			while (iter.hasNext()) {
				RemoveButton removeButton = iter.next();
				if (source == removeButton.getContainingWidget().getElement()) {
					iter.remove();
					fireRemoveEvent(removeRow(removeButton));
				}
			}
		}
	}

	public void setAddButtonText(String newText) {
		view.getAddButton().setText(newText);
	}

	public void hideRemoveButtons() {
		view.hideRemoveButtons();
	}

	public AddButton getAddButton() {
		return view.getAddButton();
	}
}
