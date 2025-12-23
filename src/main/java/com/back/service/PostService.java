package com.back.service;

import com.back.entity.Member;
import com.back.entity.Post;
import com.back.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long count(){
        return postRepository.count();
    }

    public Post write(Member author, String title, String content){
        Post post = new Post(author, title, content);
        return postRepository.save(post);
        /**
         *  postRepository.save(post); 시
         *  DB에 INSERT
         *  ID 생성
         *  createdAt, updateAt 등 채워짐
         *  엔티티가 영속 상태(Persistnet)로 전환됨.
         */
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }
}
