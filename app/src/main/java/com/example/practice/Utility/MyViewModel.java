package com.example.practice.Utility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MutableLiveData<String> getText() {
        if(mText==null) {
            mText = new MutableLiveData<>();
        }
        return mText;
    }

    public void setTextView(String s) {
        mText.setValue(mText.getValue()+s);
    }
}
