package arduito



import org.junit.*
import grails.test.mixin.*

/**
 * EdificioControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(EdificioController)
@Mock(Edificio)
class EdificioControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/edificio/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.edificioInstanceList.size() == 0
        assert model.edificioInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.edificioInstance != null
    }

    void testSave() {
        controller.save()

        assert model.edificioInstance != null
        assert view == '/edificio/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/edificio/show/1'
        assert controller.flash.message != null
        assert Edificio.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/edificio/list'


        populateValidParams(params)
        def edificio = new Edificio(params)

        assert edificio.save() != null

        params.id = edificio.id

        def model = controller.show()

        assert model.edificioInstance == edificio
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/edificio/list'


        populateValidParams(params)
        def edificio = new Edificio(params)

        assert edificio.save() != null

        params.id = edificio.id

        def model = controller.edit()

        assert model.edificioInstance == edificio
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/edificio/list'

        response.reset()


        populateValidParams(params)
        def edificio = new Edificio(params)

        assert edificio.save() != null

        // test invalid parameters in update
        params.id = edificio.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/edificio/edit"
        assert model.edificioInstance != null

        edificio.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/edificio/show/$edificio.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        edificio.clearErrors()

        populateValidParams(params)
        params.id = edificio.id
        params.version = -1
        controller.update()

        assert view == "/edificio/edit"
        assert model.edificioInstance != null
        assert model.edificioInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/edificio/list'

        response.reset()

        populateValidParams(params)
        def edificio = new Edificio(params)

        assert edificio.save() != null
        assert Edificio.count() == 1

        params.id = edificio.id

        controller.delete()

        assert Edificio.count() == 0
        assert Edificio.get(edificio.id) == null
        assert response.redirectedUrl == '/edificio/list'
    }
}
