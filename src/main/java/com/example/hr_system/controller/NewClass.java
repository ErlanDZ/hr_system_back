package com.example.hr_system.controller;//package com.example.hr_system.controller;
//
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//public class NewClass {
//        private Map<Long, List<SseEmitter>> userEmitters = new HashMap<>(); // Храните emitters  для пользователей
//
//    @PostMapping("/change-status")
//    public ResponseEntity<String> changeStatus(@RequestBody StatusChangeEvent event) {
//        // Сохраните событие в базе данных как объект события
//        Event savedEvent = eventService.saveEvent(event);
//
//        // Извлекать отправители для пользователей,
//        // зарегистрированных для получения обновлений этого пользователя
//        List<SseEmitter> emitters = userEmitters.get(event.getUserId());
//
//        // Отправьте событие зарегистрированным пользователям
//        if (emitters != null) {
//            for (SseEmitter emitter : emitters) {
//                try {
//                    emitter.send(SseEmitter.event().data(savedEvent));
//                } catch (IOException e) {
//                    // Handle emitter error
//                }
//            }
//        }
//
//        return ResponseEntity.ok("Status changed successfully.");
//    }
//
//    @GetMapping("/register")
//    public SseEmitter registerForUpdates(@RequestParam Long userId) {
//        SseEmitter emitter = new SseEmitter();
//        // Добавьте излучатель на карту пользовательских излучателей
//        userEmitters.computeIfAbsent(userId, k -> new ArrayList<>()).add(emitter);
//
//        emitter.onCompletion(() -> {
//            //Извлеките излучатель после завершения работы или отсоединения
//            userEmitters.get(userId).remove(emitter);
//        });
//
//        return emitter;
//    }
//}
