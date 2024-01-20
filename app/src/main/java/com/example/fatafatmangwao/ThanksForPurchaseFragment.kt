package com.example.fatafatmangwao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.fatafatmangwao.activities.MainActivity

class ThanksForPurchaseFragment : Fragment() {

    private val mainActivity = MainActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.userCategoryFragment, false)
        }
        return inflater.inflate(R.layout.fragment_thanks_for_purchase, container, false)
    }
}