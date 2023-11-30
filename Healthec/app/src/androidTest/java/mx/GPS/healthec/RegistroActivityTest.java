package mx.GPS.healthec;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistroActivityTest {

    @Rule
    public ActivityScenarioRule<RegistroActivity> activityScenarioRule =
            new ActivityScenarioRule<>(RegistroActivity.class);

    @Test
    public void testRegistroActivity() {
        // Ingresa el texto en los campos de texto
        Espresso.onView(ViewMatchers.withId(R.id.edt_correoRegistro))
                .perform(ViewActions.typeText("test@example.com"));

        Espresso.onView(ViewMatchers.withId(R.id.edt_passwordRegistro))
                .perform(ViewActions.typeText("password"));

        Espresso.onView(ViewMatchers.withId(R.id.edt_nombreRegistro))
                .perform(ViewActions.typeText("Test User"));

        // Realiza clic en el botón de registro
        Espresso.onView(ViewMatchers.withId(R.id.btn_registroAccept))
                .perform(ViewActions.click());
    }
}
