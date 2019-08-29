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

    def "test message inserted by application startup"() {
        when:
        Message message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then:
        message.title == "myMessage"
    }

    def "test method invoked few times"() {
        when:
        Message message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then:
        message.title == "myMessage"
        messageService.invocationCounter == 1

        when:
        message = messageService.findMessageByTitle(MESSAGE_TITLE)

        then:
        message.title == "myMessage"
        messageService.invocationCounter == 2
    }
}
