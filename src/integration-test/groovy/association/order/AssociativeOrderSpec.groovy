package association.order


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*
import org.grails.async.factory.gpars.GparsPromiseFactory
import grails.async.Promises

@Integration
@Rollback
class AssociativeOrderSpec extends Specification {

    def setup() {
        Promises.promiseFactory = new GparsPromiseFactory()
    	def container = new Container(name: 'Test Container')
    	container.addToPorts(new ContainerPort(port: 80))
    	container.addToPorts(new ContainerPort(port:443))
    	container.save(flush:true)
    }

    def cleanup() {
    	Container.first().delete()
    }

    void "it should consistently load ports in the same order"() {
    	given:
    		def matchCount = 0
            def id = Container.first()?.id
    		def match = [80,443]
    	when:
    		10.times {
    			Container.withNewSession { session ->
    				def ports = Container.get(id).ports.collect{cnt -> cnt.port}
    				if(match == ports) {
    					matchCount++
    				}
    			}	
    		}
    		
    	then:
    		matchCount == 10
    }
}
