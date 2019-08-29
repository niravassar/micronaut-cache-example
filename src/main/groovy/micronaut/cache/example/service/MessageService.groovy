package micronaut.cache.example.service

import micronaut.cache.example.domain.Message

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageService {

    @Inject MessageDataService messageDataService
    int invocationCounter = 0

    Message findMessageByTitle(String title) {
        ++invocationCounter
        Message message = messageDataService.findByTitle(title)
        message
    }
}