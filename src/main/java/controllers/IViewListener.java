package controllers;

public interface IViewListener {
  enum Event {OPEN_FILE, GENERATE_GRAPH, SAVE_CHART}

  void eventFired(Event event, Object o);
}
