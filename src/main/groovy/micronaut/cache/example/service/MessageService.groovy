package micronaut.cache.example.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageService {

    @Inject MessageDataService messageDataService
}