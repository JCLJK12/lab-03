package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int position, City city);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        String cityName = null;
        String provinceName = null;
        int position;
        boolean isEdit;

        // check if bundle exists
        if (args != null) {
            cityName = args.getString("cityName");
            provinceName = args.getString("provinceName");
            position = args.getInt("position", -1);
        } else {
            position = 0;
        }

        // check if city and province name contains a string to access edit mode
        if (cityName != null && provinceName != null) {
            isEdit = true;
            // display current city and province to be edited
            editCityName.setText(cityName);
            editProvinceName.setText(provinceName);

        } else {
            isEdit = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit City" : "Add a City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Update" : "Add", (dialog, which) -> {
                    String newCityName = editCityName.getText().toString();
                    String newProvinceName = editProvinceName.getText().toString();
                    City city = new City(newCityName, newProvinceName);

                    // if editing, allow updates, otherwise add the city
                    if (isEdit) {
                        listener.updateCity(position, city);
                    } else {
                        listener.addCity(city);
                    }
                })
                .create();
    }
    public static AddCityFragment newInstance(City city, int position) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putString("cityName", city.getCity());
        args.putString("provinceName", city.getProvince());
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
}
