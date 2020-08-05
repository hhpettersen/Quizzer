package com.example.quizzer.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.quizzer.R
import com.example.quizzer.data.entities.Game
import com.example.quizzer.data.entities.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.login_fragment.view.*
import timber.log.Timber

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        view.loginButton.setOnClickListener {
            launchSignInFlow()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        // Observe the authentication state so we can know if the user has logged in successfully.
        // If the user has logged in successfully, bring them back to the home screen.
        // If the user did not log in successfully, display an error message.
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(R.id.action_loginFragment_to_startFragment)
                LoginViewModel.AuthenticationState.INVALID_AUTHENTICATION -> Snackbar.make(
                    view, requireActivity().getString(R.string.login_unsuccessful_msg),
                    Snackbar.LENGTH_LONG
                ).show()
                else -> Timber.e(
                    "Authentication state that doesn't require any UI change $authenticationState"
                )
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                newUserFlow()
                // Successfully signed in user.
                Timber.i(
                    "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
            } else {
                // Sign in failed. If response is null the user canceled the sign-in flow using
                // the back button. Otherwise check response.getError().getErrorCode() and handle
                // the error.
                Timber.i("Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(), AuthUI.IdpConfig.GoogleBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                providers
            ).build(), SIGN_IN_RESULT_CODE
        )
    }

    private fun newUserFlow() {
        val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                val userExists = uid?.let { snapshot.child("users").hasChild(it) }
                if(!userExists!!) {
                    createUserInDb(uid)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Database Error occurred", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun createUserInDb(uid: String) {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        val email = FirebaseAuth.getInstance().currentUser?.email

        val userNode = User(uid, email,"", Game(0,0,""))

        ref.child(uid).setValue(userNode).addOnCompleteListener {
                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
        }
    }
}
