package com.github.swfox.trambient.light.ui.observer;

public class ValueObservable<T> extends Observable {

    private T value;

    public ValueObservable(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        notifyObservers();
    }

}
