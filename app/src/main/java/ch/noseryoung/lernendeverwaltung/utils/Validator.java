package ch.noseryoung.lernendeverwaltung.utils;

import android.widget.EditText;

import java.util.LinkedList;
import java.util.regex.Pattern;

public class Validator {

    public Validator() {
    }

    //FUnction to Validate a Linked List of Text Fields
    public boolean isValid(LinkedList<EditText> form) {
        boolean fieldsOK = false;
        for (EditText editText : form) {
            if (editText.getText().length() == 0) {
                editText.setError("Eingabe obligatorisch");
                fieldsOK = false;
            } else if (editText.getText().length() > 50) {
                editText.setError("Die Eingabe ist zu lang (max 50 Zeichen)");
            } else if (!Pattern.matches("[a-zA-ZäöüÄÖÜ]+", editText.getText())) {
                editText.setError("Es dürfen nur Buchstaben verwendet werden");
                fieldsOK = false;
            } else {
                fieldsOK = true;
            }
        }
        return fieldsOK;
    }
}
