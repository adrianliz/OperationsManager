package controllers;

public interface IViewListener {
  enum Event {OPEN, GENERATE_CHART};

  void eventFired(Event event, Object o);
}
