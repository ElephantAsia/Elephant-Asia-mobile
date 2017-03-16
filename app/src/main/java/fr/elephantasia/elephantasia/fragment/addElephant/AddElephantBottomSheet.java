package fr.elephantasia.elephantasia.fragment.addElephant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.elephantasia.elephantasia.R;

public class AddElephantBottomSheet extends BottomSheetDialogFragment {

    public static AddElephantBottomSheet newInstance() {
        return new AddElephantBottomSheet();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_elephant_bottom_sheet, container, false);

        v.findViewById(R.id.add_elephant_menu_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
