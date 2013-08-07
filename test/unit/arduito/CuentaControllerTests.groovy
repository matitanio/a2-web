package arduito



import org.junit.*
import grails.test.mixin.*

/**
 * CuentaControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(CuentaController)
@Mock(Cuenta)
class CuentaControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/cuenta/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.cuentaInstanceList.size() == 0
        assert model.cuentaInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.cuentaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.cuentaInstance != null
        assert view == '/cuenta/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/cuenta/show/1'
        assert controller.flash.message != null
        assert Cuenta.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/cuenta/list'


        populateValidParams(params)
        def cuenta = new Cuenta(params)

        assert cuenta.save() != null

        params.id = cuenta.id

        def model = controller.show()

        assert model.cuentaInstance == cuenta
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/cuenta/list'


        populateValidParams(params)
        def cuenta = new Cuenta(params)

        assert cuenta.save() != null

        params.id = cuenta.id

        def model = controller.edit()

        assert model.cuentaInstance == cuenta
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/cuenta/list'

        response.reset()


        populateValidParams(params)
        def cuenta = new Cuenta(params)

        assert cuenta.save() != null

        // test invalid parameters in update
        params.id = cuenta.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/cuenta/edit"
        assert model.cuentaInstance != null

        cuenta.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/cuenta/show/$cuenta.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        cuenta.clearErrors()

        populateValidParams(params)
        params.id = cuenta.id
        params.version = -1
        controller.update()

        assert view == "/cuenta/edit"
        assert model.cuentaInstance != null
        assert model.cuentaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/cuenta/list'

        response.reset()

        populateValidParams(params)
        def cuenta = new Cuenta(params)

        assert cuenta.save() != null
        assert Cuenta.count() == 1

        params.id = cuenta.id

        controller.delete()

        assert Cuenta.count() == 0
        assert Cuenta.get(cuenta.id) == null
        assert response.redirectedUrl == '/cuenta/list'
    }
}
