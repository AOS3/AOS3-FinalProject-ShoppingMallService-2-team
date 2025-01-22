package com.nemodream.bangkkujaengi.customer.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nemodream.bangkkujaengi.databinding.ItemSocialGalleryPhotoBinding

class SocialGalleryAdapter(
    private val photoList: List<Uri>,
    private val context: Context // Context를 어댑터에 전달받음
) : RecyclerView.Adapter<SocialGalleryAdapter.SocialGalleryViewHolder>() {

    private val selectedPhotos = mutableListOf<Uri>() // 선택된 사진들을 저장하는 리스트
    private val maxSelectionLimit = 10 // 최대 선택 가능 수

    inner class SocialGalleryViewHolder(private val binding: ItemSocialGalleryPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoUri: Uri) {
            // Glide.with(context) 도 가능하다. 생성자로 context 받아왔기 때문이다.
            Glide.with(binding.root.context)
                .load(photoUri)
                .into(binding.ivPhoto)

            // 선택 동작 처리
            updateSelectionState(photoUri)

            binding.root.setOnClickListener {
                if (selectedPhotos.contains(photoUri)) {
                    // 이미 선택된 경우 선택 해제
                    selectedPhotos.remove(photoUri)
                } else {
                    if (selectedPhotos.size >= maxSelectionLimit) {
                        // 선택 가능 수 초과 시 Toast 메시지 표시
                        Toast.makeText(context, "사진은 10개까지 선택 가능합니다", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    // 선택 추가
                    selectedPhotos.add(photoUri)
                }
                // 선택 상태 갱신
                updateSelectionState(photoUri)
            }
        }

        private fun updateSelectionState(photoUri: Uri) {
            val isSelected = selectedPhotos.contains(photoUri)
            binding.selectionCircle.isSelected = isSelected // 선택 상태 반영
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialGalleryViewHolder {
        val binding = ItemSocialGalleryPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SocialGalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SocialGalleryViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    fun getSelectedPhotos(): List<Uri> = selectedPhotos // 선택된 사진 리스트 반환
}
