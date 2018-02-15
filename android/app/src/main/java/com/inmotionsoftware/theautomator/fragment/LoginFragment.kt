package com.inmotionsoftware.theautomator.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.inmotionsoftware.theautomator.*
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * Created by jhunt on 2/1/18.
 */
class LoginFragment : BaseFragment() {


    private val superSecureEmail = "test@gmail.com"
    private val superSecurePassword = "111111"
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            loginButton.isEnabled = checkValidInput(emailEditText.text.toString(),passwordEditText.text.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            checkCredentials(emailEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    private fun checkCredentials(email: String, password: String) {

        if (checkValidInput(email, password) && email == superSecureEmail && password == superSecurePassword) {
            activity?.fragmentVisibilityManager()?.showLoadingFragment()
        } else
            Toast.makeText(context, "invalid credentials", Toast.LENGTH_SHORT).show()
    }

    private fun checkValidInput(email: String, password: String): Boolean {

        val validEmail = com.inmotionsoftware.nobugs.isValidEmail(email)
        val validPassword = com.inmotionsoftware.nobugs.isValidPassword(password)

        return validEmail && validPassword
    }

    override fun cleanUp() {

    }

}