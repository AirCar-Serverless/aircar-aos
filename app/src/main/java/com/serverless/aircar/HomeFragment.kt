package com.serverless.aircar

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kakao.util.maps.helper.Utility
import com.serverless.aircar.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val mapView = MapView(context)
        binding.mapView.addView(mapView)

        return binding.root
    }

    fun getKeyHash(context: Context): String? {
        val packageInfo =
            Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (e: NoSuchAlgorithmException) {
                Log.w("jaemin", "디버그 keyHash" + signature, e);
            }
        }
        return null;
    }
}