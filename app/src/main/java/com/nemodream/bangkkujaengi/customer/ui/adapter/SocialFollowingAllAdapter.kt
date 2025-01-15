package com.nemodream.bangkkujaengi.customer.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nemodream.bangkkujaengi.customer.data.model.Member
import com.nemodream.bangkkujaengi.databinding.RowSocialFollowingAllBinding
import com.nemodream.bangkkujaengi.utils.loadImage

class SocialFollowingAllAdapter : RecyclerView.Adapter<SocialFollowingAllAdapter.SocialFollowingAllViewHolder>() {
    private val items = mutableListOf<Member>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialFollowingAllViewHolder {
        val binding = RowSocialFollowingAllBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SocialFollowingAllViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SocialFollowingAllViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(socialFollowingAllRows: List<Member>) {
        items.clear()
        items.addAll(socialFollowingAllRows)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SocialFollowingAllViewHolder(private val binding: RowSocialFollowingAllBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isFollowing = true

        fun bind(member: Member) {
            binding.ivProfileImage.loadImage(member.memberProfileImage.toString())
            binding.tvProfileNickname.text = member.memberNickName

            // 초기 상태 설정 (isFollowing 여부에 따라)
            updateButtonState(isFollowing)

            // 버튼 클릭 리스너
            binding.btnFollowingAllFollowing.setOnClickListener {
                isFollowing = !isFollowing
                updateButtonState(isFollowing)

                if (isFollowing) {
                    // 팔로우 목록에 멤버 추가 (나중에 구현 예정)
                } else {
                    // 팔로우 목록에서 멤버 제거 (나중에 구현 예정)
                }
            }
        }

        private fun updateButtonState(isFollowing: Boolean) {
            if (isFollowing) {
                binding.btnFollowingAllFollowing.text = "팔로잉"
                binding.btnFollowingAllFollowing.setTextColor(Color.parseColor("#58443B")) // 글자색
                binding.btnFollowingAllFollowing.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#DFDFDF")) // 버튼 배경색
                )
                binding.btnFollowingAllFollowing.strokeColor =
                    ColorStateList.valueOf(Color.parseColor("#818080")) // 테두리 색상
                binding.btnFollowingAllFollowing.strokeWidth = 1 // 테두리 두께 (px 단위)
            } else {
                binding.btnFollowingAllFollowing.text = "팔로우"
                binding.btnFollowingAllFollowing.setTextColor(Color.parseColor("#FFFFFF")) // 글자색
                binding.btnFollowingAllFollowing.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor("#332828")) // 버튼 배경색
                )
                binding.btnFollowingAllFollowing.strokeColor =
                    ColorStateList.valueOf(Color.parseColor("#000000")) // 테두리 색상 (기본값, 변경 가능)
                binding.btnFollowingAllFollowing.strokeWidth = 1 // 테두리 두께 (px 단위)
            }
        }
    }
}