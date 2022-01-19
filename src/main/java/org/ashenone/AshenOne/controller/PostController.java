package org.ashenone.AshenOne.controller;

import org.ashenone.AshenOne.domain.Post;
import org.ashenone.AshenOne.domain.User;
import org.ashenone.AshenOne.domain.dto.PostDto;
import org.ashenone.AshenOne.repos.PostRepo;
import org.ashenone.AshenOne.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class PostController
{
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostService postService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model)
    {
        return "greeting";
    }

    @GetMapping("/posts")
    public String posts(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(
                    sort = {"id"},
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        Page<PostDto> page = postService.postList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/posts");
        model.addAttribute("filter", filter);

        return "posts";
    }

    @PostMapping("/posts")
    public String addPost(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            @RequestParam("file") MultipartFile file,
            Model model,
            @PageableDefault(
                    sort = { "id" },
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) throws IOException
    {
        post.setAuthor(user);

        if (bindingResult.hasErrors())
        {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("post", post);
        }
        else
        {
            saveFile(post, file);
            model.addAttribute("post", null);
            postRepo.save(post);
        }

//        String url = ControllerUtils.redirectUpdatedPage(redirectAttributes, referer);

        model.addAttribute("url", "/posts");
        Page<PostDto> page = this.postService.postList(pageable, "", user);
        model.addAttribute("page", page);

        return "posts";
//        return "main";
    }

    private void saveFile(
            @Valid Post post,
            @RequestParam("file") MultipartFile file
    ) throws IOException
    {
        if (file != null && !file.getOriginalFilename().isEmpty())
        {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) uploadDir.mkdir();

            String uuidFile = UUID.randomUUID().toString();
            String filename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + filename));

            post.setFilename(filename);
        }
    }

    @GetMapping("/user-posts/{author}")
    public String userPosts(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Post post,
            @PageableDefault(
                    sort = { "id" },
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        Page<PostDto> page = postService.postListForUser(pageable, currentUser, author);

        model.addAttribute("userProfile", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isFollower", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("post", post);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-posts/" + author.getId());

        return "user_posts";
    }

    @PostMapping("/user-posts/{user}")
    public String updatePost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long userId,
            @RequestParam("id") Post post,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file,
            @PageableDefault(
                    sort = {"id"},
                    direction = Sort.Direction.DESC
            ) Pageable pageable,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) throws IOException
    {
        if (post.getAuthor().equals(currentUser))
        {
            if (!text.isEmpty()) post.setText(text);
            if (!tag.isEmpty()) post.setTag(tag);

            saveFile(post, file);
            postRepo.save(post);
        }

        return ControllerUtils.redirectUpdatedPage(redirectAttributes, referer);
//        return "redirect:/user-posts/" + userId;
    }

    @GetMapping("/del-user-posts/{userId}")
    public String deletePost(
            @PathVariable Long userId,
            @RequestParam("post") Long postId,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) throws IOException
    {
        postRepo.deleteById(postId);
        return ControllerUtils.redirectUpdatedPage(redirectAttributes, referer);
//        return "redirect:/user-posts/" + userId;
    }

    @GetMapping("/posts/{post}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Post post,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = post.getLikes();

        if (likes.contains(currentUser)) likes.remove(currentUser);
        else likes.add(currentUser);

        return ControllerUtils.redirectUpdatedPage(redirectAttributes, referer);
    }

//    @ExceptionHandler(BindException.class)
//    @ResponseBody
//    public Map<String, String> handleBindException(BindException ex)
//    {
//        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
//                fieldError -> fieldError.getField() + "Error",
//                FieldError::getDefaultMessage
//        );
//        Map<String, String> errorsMap = ex.getBindingResult().getFieldErrors().stream().collect(collector);
//
//        return errorsMap;
//    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder)
//    {
//        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//    }
}