package fr.elefantasia.elefantasia.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import fr.elefantasia.elefantasia.utils.StaticTools;

/**
 * Created by salete_s on 04/01/17.
 */

public class EA_EditText extends EditText {

    public EA_EditText(Context context) {
        super(context);

    }

    public EA_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public EA_EditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (!focused) {
            StaticTools.hideKeyboard(getContext(), this);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

}
