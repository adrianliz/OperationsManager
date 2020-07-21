package controllers;

public interface IViewListener {
  enum Event {OPEN, ACCEPT_STATISTIC};

  void eventFired(Event event, Object o);
}
