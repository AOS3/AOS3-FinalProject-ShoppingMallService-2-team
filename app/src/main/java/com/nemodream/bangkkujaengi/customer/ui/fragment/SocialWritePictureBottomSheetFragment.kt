package com.nemodream.bangkkujaengi.customer.ui.fragment

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nemodream.bangkkujaengi.databinding.FragmentSocialWritePictureBottomSheetBinding
import com.nemodream.bangkkujaengi.customer.ui.adapter.SocialGalleryAdapter

class SocialWritePictureBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSocialWritePictureBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialWritePictureBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadGalleryPhotos(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadGalleryPhotos(context: Context) {
        val photoList = fetchGalleryPhotos(context)
        val adapter = SocialGalleryAdapter(photoList)

        binding.recyclerViewGallery.layoutManager = GridLayoutManager(context, 3)
        binding.recyclerViewGallery.adapter = adapter
    }

    private fun fetchGalleryPhotos(context: Context): List<Uri> {
        val photoList = mutableListOf<Uri>()
        val contentResolver: ContentResolver = context.contentResolver
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)

        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val photoUri = Uri.withAppendedPath(uri, id.toString())
                photoList.add(photoUri)
            }
        }

        return photoList
    }
}
