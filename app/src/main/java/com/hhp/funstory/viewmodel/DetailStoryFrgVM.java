package com.hhp.funstory.viewmodel;

import androidx.lifecycle.ViewModel;

public class DetailStoryFrgVM extends ViewModel {
    private int currentIndex;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
