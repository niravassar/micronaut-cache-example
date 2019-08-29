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

    @Inject MessageService messageService

    def "test message inserted by application startup"() {
        when:
        Message message = messageService.findMessageByTitle("myMessage")

        then:
        message.title == "myMessage"
    }
}
