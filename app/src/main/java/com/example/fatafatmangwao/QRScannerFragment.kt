package com.example.fatafatmangwao

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.example.fatafatmangwao.databinding.FragmentQRScannerBinding
import com.example.fatafatmangwao.model.cart.Cart
import com.example.fatafatmangwao.utils.Extensions.gone
import com.example.fatafatmangwao.utils.Extensions.showToast
import com.example.fatafatmangwao.utils.Extensions.visible
import com.example.fatafatmangwao.viewmodel.SharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QRScannerFragment : Fragment() {

    private lateinit var mBinding: FragmentQRScannerBinding
    private lateinit var codeScanner: CodeScanner
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentQRScannerBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            cartImg.setOnClickListener {
                val bundle = Bundle()
                bundle.putBoolean("fromQR", true)
                findNavController().navigate(R.id.confirmOrderFragment, bundle)
            }
            codeScanner = CodeScanner(requireActivity(), scannerView)
            codeScanner.decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread {
                    val data = it.text
                    val productId = data.substring(data.length-1)
                    val supermarketId = data.substring(0, data.length-1)
                    if(sharedViewModel.supermarketId.equals("Imtiyaaz001")) {
                        if(sharedViewModel.supermarketId.equals(supermarketId)) {
                            if(productId == "1") {
                                sharedViewModel.supermarketCartItems.add(Cart(title = "Italiano cuisine original black pepper, 100 grams", imageDrawable = R.drawable.blackpepper, quantity = 1, price = 400))
                            } else if(productId == "2") {
                                sharedViewModel.supermarketCartItems.add(Cart(title = "Sabroso pure white chicken, 1kg", imageDrawable = R.drawable.food, quantity = 1, price = 400))
                            } else if(productId == "3") {
                                sharedViewModel.supermarketCartItems.add(Cart(title = "Knorr noodles, spicy in favlour", imageDrawable = R.drawable.food, quantity = 1, price = 400))
                            }
                        } else {
                            requireContext().showToast("You cant add this item into cart", Toast.LENGTH_SHORT)
                            findNavController().popBackStack()
                        }
                    } else if (sharedViewModel.supermarketId.equals("Dawood002")) {
                        if(sharedViewModel.supermarketId.equals(supermarketId)) {
                            if(productId == "1") {
                                sharedViewModel.supermarketCartItems.add(Cart(title = "Italiano cuisine original black pepper, 100 grams", imageDrawable = R.drawable.blackpepper, quantity = 1, price = 400))
                            } else if(productId == "2") {
                                sharedViewModel.supermarketCartItems.add(Cart(title = "Sabroso pure white chicken, 1kg", imageDrawable = R.drawable.food, quantity = 1, price = 400))
                            }
                        } else {
                            requireContext().showToast("You cant add this item into cart", Toast.LENGTH_SHORT)
                            findNavController().popBackStack()
                        }
                    }

                    requireContext().showToast("Item added to cart", Toast.LENGTH_SHORT)
                    Log.d("TAG", "data from barcode $productId $supermarketId")
                    lifecycleScope.launch {
                        scannerView.gone()
                        loadingView.playAnimation()
                        loadingView.visible()
                        delay(2000)
                        loadingView.gone()
                        scannerView.visible()
                        codeScanner.startPreview()
                    }
                }
            }
            if (checkCameraPermission()) {
                codeScanner.startPreview()
            } else {
                requestCameraPermission()
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA),
            100
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                codeScanner.startPreview()
            } else {
                requestCameraPermission()
            }
        }
    }
    override fun onResume() {
        codeScanner.startPreview()
        super.onResume()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}
