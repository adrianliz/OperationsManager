package controllers;

public interface IViewListener {
  enum Event {OPEN_FILE, GENERATE_CHART, SAVE_CHART};

  void eventFired(Event event, Object o);
}
