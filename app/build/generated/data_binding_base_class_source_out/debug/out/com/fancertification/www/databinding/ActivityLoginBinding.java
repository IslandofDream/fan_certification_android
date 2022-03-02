// Generated by view binder compiler. Do not edit!
package com.fancertification.www.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.fancertification.www.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button LoginBtn;

  @NonNull
  public final Button cancleBtn;

  @NonNull
  public final CheckBox checkFemale;

  @NonNull
  public final CheckBox checkMale;

  @NonNull
  public final TextInputEditText emailSignupText;

  @NonNull
  public final TextInputEditText emailText;

  @NonNull
  public final TextView forgotPW;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextInputLayout loginEmailInputLayout;

  @NonNull
  public final ConstraintLayout loginLayout;

  @NonNull
  public final TextInputLayout loginpwInputLayout2;

  @NonNull
  public final TextInputEditText nameSignupText;

  @NonNull
  public final Button okBtn;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextInputEditText pwSignupText;

  @NonNull
  public final TextInputEditText pwText;

  @NonNull
  public final LinearLayout selectGender;

  @NonNull
  public final Button signupBtn;

  @NonNull
  public final TextInputLayout signupInputLayout;

  @NonNull
  public final TextInputLayout signupInputLayout2;

  @NonNull
  public final TextInputLayout signupInputLayout3;

  @NonNull
  public final ConstraintLayout signupPopup;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView textView5;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull Button LoginBtn,
      @NonNull Button cancleBtn, @NonNull CheckBox checkFemale, @NonNull CheckBox checkMale,
      @NonNull TextInputEditText emailSignupText, @NonNull TextInputEditText emailText,
      @NonNull TextView forgotPW, @NonNull ImageView imageView, @NonNull ImageView imageView2,
      @NonNull LinearLayout linearLayout, @NonNull TextInputLayout loginEmailInputLayout,
      @NonNull ConstraintLayout loginLayout, @NonNull TextInputLayout loginpwInputLayout2,
      @NonNull TextInputEditText nameSignupText, @NonNull Button okBtn,
      @NonNull ProgressBar progressBar, @NonNull TextInputEditText pwSignupText,
      @NonNull TextInputEditText pwText, @NonNull LinearLayout selectGender,
      @NonNull Button signupBtn, @NonNull TextInputLayout signupInputLayout,
      @NonNull TextInputLayout signupInputLayout2, @NonNull TextInputLayout signupInputLayout3,
      @NonNull ConstraintLayout signupPopup, @NonNull TextView textView3,
      @NonNull TextView textView5) {
    this.rootView = rootView;
    this.LoginBtn = LoginBtn;
    this.cancleBtn = cancleBtn;
    this.checkFemale = checkFemale;
    this.checkMale = checkMale;
    this.emailSignupText = emailSignupText;
    this.emailText = emailText;
    this.forgotPW = forgotPW;
    this.imageView = imageView;
    this.imageView2 = imageView2;
    this.linearLayout = linearLayout;
    this.loginEmailInputLayout = loginEmailInputLayout;
    this.loginLayout = loginLayout;
    this.loginpwInputLayout2 = loginpwInputLayout2;
    this.nameSignupText = nameSignupText;
    this.okBtn = okBtn;
    this.progressBar = progressBar;
    this.pwSignupText = pwSignupText;
    this.pwText = pwText;
    this.selectGender = selectGender;
    this.signupBtn = signupBtn;
    this.signupInputLayout = signupInputLayout;
    this.signupInputLayout2 = signupInputLayout2;
    this.signupInputLayout3 = signupInputLayout3;
    this.signupPopup = signupPopup;
    this.textView3 = textView3;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.LoginBtn;
      Button LoginBtn = ViewBindings.findChildViewById(rootView, id);
      if (LoginBtn == null) {
        break missingId;
      }

      id = R.id.cancleBtn;
      Button cancleBtn = ViewBindings.findChildViewById(rootView, id);
      if (cancleBtn == null) {
        break missingId;
      }

      id = R.id.checkFemale;
      CheckBox checkFemale = ViewBindings.findChildViewById(rootView, id);
      if (checkFemale == null) {
        break missingId;
      }

      id = R.id.checkMale;
      CheckBox checkMale = ViewBindings.findChildViewById(rootView, id);
      if (checkMale == null) {
        break missingId;
      }

      id = R.id.emailSignupText;
      TextInputEditText emailSignupText = ViewBindings.findChildViewById(rootView, id);
      if (emailSignupText == null) {
        break missingId;
      }

      id = R.id.emailText;
      TextInputEditText emailText = ViewBindings.findChildViewById(rootView, id);
      if (emailText == null) {
        break missingId;
      }

      id = R.id.forgotPW;
      TextView forgotPW = ViewBindings.findChildViewById(rootView, id);
      if (forgotPW == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.loginEmailInputLayout;
      TextInputLayout loginEmailInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (loginEmailInputLayout == null) {
        break missingId;
      }

      id = R.id.loginLayout;
      ConstraintLayout loginLayout = ViewBindings.findChildViewById(rootView, id);
      if (loginLayout == null) {
        break missingId;
      }

      id = R.id.loginpwInputLayout2;
      TextInputLayout loginpwInputLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (loginpwInputLayout2 == null) {
        break missingId;
      }

      id = R.id.nameSignupText;
      TextInputEditText nameSignupText = ViewBindings.findChildViewById(rootView, id);
      if (nameSignupText == null) {
        break missingId;
      }

      id = R.id.okBtn;
      Button okBtn = ViewBindings.findChildViewById(rootView, id);
      if (okBtn == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.pwSignupText;
      TextInputEditText pwSignupText = ViewBindings.findChildViewById(rootView, id);
      if (pwSignupText == null) {
        break missingId;
      }

      id = R.id.pwText;
      TextInputEditText pwText = ViewBindings.findChildViewById(rootView, id);
      if (pwText == null) {
        break missingId;
      }

      id = R.id.selectGender;
      LinearLayout selectGender = ViewBindings.findChildViewById(rootView, id);
      if (selectGender == null) {
        break missingId;
      }

      id = R.id.signupBtn;
      Button signupBtn = ViewBindings.findChildViewById(rootView, id);
      if (signupBtn == null) {
        break missingId;
      }

      id = R.id.signupInputLayout;
      TextInputLayout signupInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (signupInputLayout == null) {
        break missingId;
      }

      id = R.id.signupInputLayout2;
      TextInputLayout signupInputLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (signupInputLayout2 == null) {
        break missingId;
      }

      id = R.id.signupInputLayout3;
      TextInputLayout signupInputLayout3 = ViewBindings.findChildViewById(rootView, id);
      if (signupInputLayout3 == null) {
        break missingId;
      }

      id = R.id.signupPopup;
      ConstraintLayout signupPopup = ViewBindings.findChildViewById(rootView, id);
      if (signupPopup == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, LoginBtn, cancleBtn, checkFemale,
          checkMale, emailSignupText, emailText, forgotPW, imageView, imageView2, linearLayout,
          loginEmailInputLayout, loginLayout, loginpwInputLayout2, nameSignupText, okBtn,
          progressBar, pwSignupText, pwText, selectGender, signupBtn, signupInputLayout,
          signupInputLayout2, signupInputLayout3, signupPopup, textView3, textView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
