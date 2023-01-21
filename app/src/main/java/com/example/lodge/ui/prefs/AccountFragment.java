package com.example.lodge.ui.prefs;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.lodge.R;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AccountFragment extends PreferenceFragmentCompat {
    private static final String FILE_NAME="encriptation_shared_pref";
    private static final String KEY_PASSWORD="key_password";
    SharedPreferences sharedPreferences;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.account_preferences,rootKey);
        initPreferenceEmail();
        //Primero se tiene que crear el fichero de encriptación
        initEncriptation();
        initPreferencePassword();
    }

    private void initPreferencePassword() {
        EditTextPreference edPassword=getPreferenceManager().findPreference(getString(R.string.key_password));
        //Se va a establecer dos eventos. El de inicialización
        edPassword.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                //se va a inicializar el tipo dato que acepta el campo EDITEXT android:inputType, no existe en las preferencias
                edPassword.setText(sharedPreferences.getString(KEY_PASSWORD,""));
                editText.setInputType(TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_PASSWORD);
                editText.selectAll();

            }
        });
        //El de modificación de la preferencia
        edPassword.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                sharedPreferences.edit().putString(KEY_PASSWORD,(String) newValue).commit();

                return true;
            }
        });
    }

    private void initEncriptation() {
        try {
        MasterKey mainKey = new MasterKey.Builder(getContext())
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();


             sharedPreferences = EncryptedSharedPreferences
                    .create(
                            getContext(),
                            FILE_NAME,
                            mainKey,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que inicializa el tipo de dato admitido en el EditText, que no esté habilitado, y
     * cuando el usuario pulsa sobre la preferencia se seleccione todoel texto
     */
    private void initPreferenceEmail() {
        EditTextPreference edEmail=getPreferenceManager().findPreference(getString(R.string.key_email));
        edEmail.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
            @Override
            public void onBindEditText(@NonNull EditText editText) {
                editText.setText(new UserPrefManager(getContext()).getUserEmail());
                editText.setEnabled(false);
            }
        });
    }
}
