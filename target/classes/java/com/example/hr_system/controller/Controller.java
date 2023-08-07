//package com.example.hr_system.controller;
//
//import lombok.AllArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//
//@RestController
//@RequestMapping("/chatting")
//@AllArgsConstructor
////@PreAuthorize("hasAnyAuthority('ADMIN')")
//@CrossOrigin(origins = "*")
//public class Controller {
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage
//    ){
//        return chatMessage;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage,
//                               SimpMessageHeaderAccessor headerAccessor){
//        //Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }
//    @GetMapping("/aed")
//    public String s(){
//        return "oj";
//    }
//}
