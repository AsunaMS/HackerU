package com.example.cocktailsproject;

import android.util.Pair;

import java.util.ArrayList;

public class MyLiveData<T> {

    private final ArrayList<Pair<String, MyObserver>> observers;
    private Object observable;
    static final Object NOT_SET = new Object();

    public MyLiveData() {
        this.observable = NOT_SET;
        this.observers = new ArrayList<>();
    }

    public void observe(MyObserver observer, String id) {
        this.observers.add(new Pair<>(id, observer));
    }

    public void removeObserver(String id) {
        for (Pair<String, MyObserver> p : observers) {
            if (p.first.equals(id)) {
                this.observers.remove(p);
            }
        }
    }

    public void removeAllObservers() {
        this.observers.clear();
    }


    public void postValue(T value) {
        this.observable = value;
        for (Pair<String, MyObserver> p : observers) {
            p.second.dataChanged();
        }
    }

    public T getValue() {
        if (observable != NOT_SET) {
            return (T) observable;
        }
        return null;
    }

}
