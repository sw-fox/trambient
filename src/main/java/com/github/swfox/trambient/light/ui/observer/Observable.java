package com.github.swfox.trambient.light.ui.observer;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {

    public final Set<Observer> observers;

    public Observable() {
        observers = new HashSet<>();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
            observers.forEach(Observer::update);
    }
}
