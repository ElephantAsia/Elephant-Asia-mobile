package fr.elefantasia.elefantasia.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;
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
            StaticTools.checkEmptyField(getContext(), this);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

}
