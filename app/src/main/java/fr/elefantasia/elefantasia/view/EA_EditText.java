package fr.elefantasia.elefantasia.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.utils.StaticTools;

/**
 * Created by salete_s on 04/01/17.
 */


//TODO: CHeck avec galibe: Demander son avis sur des composants custom avec parameter custom ?
public class EA_EditText extends EditText {
    private boolean isRequired;

    public EA_EditText(Context context) {
        super(context);

    }

    public EA_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.EA_EditText);
       isRequired = arr.getBoolean(R.styleable.EA_EditText_required_field, false);
        arr.recycle();
    }

    public EA_EditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public boolean isEmpty() {
        return getText().toString().trim().length() == 0;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if (!focused) {
            StaticTools.hideKeyboard(getContext(), this);

            if (isRequired) {
                StaticTools.checkEmptyField(getContext(), this);
            }
        }
        

    }

}
