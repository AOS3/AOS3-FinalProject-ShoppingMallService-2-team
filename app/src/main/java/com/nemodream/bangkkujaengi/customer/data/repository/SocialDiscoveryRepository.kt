package com.nemodream.bangkkujaengi.customer.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nemodream.bangkkujaengi.customer.data.model.Post
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

// 하드코딩 테스트 데이터
@Singleton
class SocialDiscoveryRepository @Inject constructor() {

    // 임시 데이터 생성
    suspend fun getPosts(): List<Post> {
        // 테스트용 더미 데이터 생성
        return listOf(
            Post(
                id = "1",
                nickname = "소셜유저",
                authorProfilePicture = "https://example.com/image1.jpg",
                title = "내방소개",
                content = "This is a test post",
                imageList = listOf("https://example.com/image1.jpg"),
                savedCount = 5,
                commentCount = 3
            ),
            Post(
                id = "2",
                nickname = "소셜유저",
                authorProfilePicture = "https://example.com/image1.jpg",
                title = "두번째 내방소개",
                content = "Another test post",
                imageList = listOf("https://example.com/image2.jpg"),
                savedCount = 10,
                commentCount = 8
            ),
            Post(
                id = "3",
                nickname = "방꾸쟁이유저",
                authorProfilePicture = "https://example.com/image2.jpg",
                title = "첫번째 내방소개",
                content = "Third test post",
                imageList = listOf("https://example.com/image3.jpg"),
                savedCount = 2,
                commentCount = 1
            ),
            Post(
                id = "4",
                nickname = "방꾸쟁이유저",
                authorProfilePicture = "https://example.com/image2.jpg",
                title = "다섯번째 내방소개",
                content = "Third test post",
                imageList = listOf("https://example.com/image3.jpg"),
                savedCount = 9,
                commentCount = 0
            ),
            Post(
                id = "5",
                nickname = "김혜인",
                authorProfilePicture = "https://example.com/image3.jpg",
                title = "방청소하기",
                content = "Third test post",
                imageList = listOf("https://example.com/image3.jpg"),
                savedCount = 0,
                commentCount = 1
            )
        )
    }
}


// 파이어 베이스 사용
//@Singleton
//class SocialDiscoveryRepository @Inject constructor(
//    private val firestore: FirebaseFirestore,
//    private val storage: FirebaseStorage,
//) {
//
//    suspend fun getPosts(): List<Post> =
//        firestore.collection("Posts")
//            .get()
//            .await()
//            .documents
//            .mapNotNull { doc ->
//                doc.toPost()
//            }
//
//    /*
//    * Firebase Firestore에서 게시글 데이터 가져오기
//    * */
//    private fun DocumentSnapshot.toPost(): Post? {
//        val id = getString("id") ?: return null
//        val nickname = getString("nickname") ?: return null
//        val title = getString("title") ?: return null
//        val content = getString("content") ?: return null
//        val imageList = get("imageList") as? List<String> ?: emptyList()
//        val savedCount = getLong("savedCount")?.toInt() ?: 0
//        val commentCount = getLong("commentCount")?.toInt() ?: 0
//
//        return Post(
//            id = id,
//            nickname = nickname,
//            title = title,
//            content = content,
//            imageList = imageList,
//            savedCount = savedCount,
//            commentCount = commentCount
//        )
//    }
//}
