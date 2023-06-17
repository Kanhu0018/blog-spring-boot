package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.blog.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

   private PostRepository postRepository;

   private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post=new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost= postRepository.save(post);

        PostDto dto=new PostDto();

      dto.setId(newPost.getId());
      dto.setTitle(newPost.getTitle());
      dto.setDescription(newPost.getDescription());
      dto.setContent(newPost.getContent());
        return dto;
    }

    @Override
    public List<PostDto> ListAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //convert String to sort
        //Sort sort = Sort.by(sortBy);

        Pageable pageable=PageRequest.of(pageNo,pageSize,sort);

        Page<Post> listOfPosts = postRepository.findAll(pageable);

        List<Post> posts = listOfPosts.getContent();

        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("post not Found with id :"+id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("PostNotFoundWithId:" + id)
        );//1st check id is present or not

        Post newPost = mapToEntity(postDto);//2nd give the dto that will convert into post and set id
        newPost.setId(id);

        Post updatedPost = postRepository.save(newPost);//save the post

        PostDto dto = mapToDto(updatedPost);//convert the updatedpost into dto and return post
        return dto;
    }

    @Override
    public void deleteById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post Not Found With Id :" + id)
        );
        postRepository.deleteById(id);
    }


    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
        /* PostDto dto=new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getDescription());
        dto.setContent(post.getContent());
        return dto;*/
      return dto;
    }

    //This method will convert Dto to Entity for update purpose
    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);

       /* Post post=new Post();
        post.setContent(postDto.getContent());
        post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        return post;*/
    return post;
    }
}
