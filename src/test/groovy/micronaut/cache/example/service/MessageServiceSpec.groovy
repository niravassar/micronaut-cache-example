package micronaut.cache.example.service

import grails.gorm.transactions.Rollback
import io.micronaut.test.annotation.MicronautTest
import micronaut.cache.example.Application
import micronaut.cache.example.domain.Message
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest(application = Application)
@Rollback
class MessageServiceSpec extends Specification {

    static final String MESSAGE_TITLE = "myMessage"

    @Inject MessageService messageService

    def setup(){
        messageService.invocationCounter = 0
    }

    def "test method cached after first invocation"() {
        when: 'called the first time'
        Message message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then: 'method runs by getting the gorm object'
        message.title == "myMessage"
        messageService.invocationCounter == 1

        when: 'called a second time'
        message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then: 'the cache is accessed and the method doesnt run again'
        message.title == "myMessage"
        messageService.invocationCounter == 1

        when: 'called again with a different param'
        message = messageService.findMessageByTitle(MESSAGE_TITLE+"Again")

        then: 'method is invoked bc cache doesnt have the stored value'
        !message
        messageService.invocationCounter == 2
    }
}
