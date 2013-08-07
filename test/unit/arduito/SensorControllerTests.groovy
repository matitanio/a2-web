package arduito



import org.junit.*
import grails.test.mixin.*

/**
 * SensorControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(SensorController)
@Mock(Sensor)
class SensorControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/sensor/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.sensorInstanceList.size() == 0
        assert model.sensorInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.sensorInstance != null
    }

    void testSave() {
        controller.save()

        assert model.sensorInstance != null
        assert view == '/sensor/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/sensor/show/1'
        assert controller.flash.message != null
        assert Sensor.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/sensor/list'


        populateValidParams(params)
        def sensor = new Sensor(params)

        assert sensor.save() != null

        params.id = sensor.id

        def model = controller.show()

        assert model.sensorInstance == sensor
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/sensor/list'


        populateValidParams(params)
        def sensor = new Sensor(params)

        assert sensor.save() != null

        params.id = sensor.id

        def model = controller.edit()

        assert model.sensorInstance == sensor
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/sensor/list'

        response.reset()


        populateValidParams(params)
        def sensor = new Sensor(params)

        assert sensor.save() != null

        // test invalid parameters in update
        params.id = sensor.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/sensor/edit"
        assert model.sensorInstance != null

        sensor.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/sensor/show/$sensor.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        sensor.clearErrors()

        populateValidParams(params)
        params.id = sensor.id
        params.version = -1
        controller.update()

        assert view == "/sensor/edit"
        assert model.sensorInstance != null
        assert model.sensorInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/sensor/list'

        response.reset()

        populateValidParams(params)
        def sensor = new Sensor(params)

        assert sensor.save() != null
        assert Sensor.count() == 1

        params.id = sensor.id

        controller.delete()

        assert Sensor.count() == 0
        assert Sensor.get(sensor.id) == null
        assert response.redirectedUrl == '/sensor/list'
    }
}
