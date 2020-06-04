import UIKit
import app

class ViewController: UIViewController, HomeView {
    
    private lazy var presenter: HomePresenter = HomePresenter(
        repository: CommonRepository(
            remote: CommonRemoteDataSource(),
            local: CommonLocalDataSource(settings: AppleSettings(delegate: UserDefaults.standard))
        ),
        errorHandler: ErrorHandler(),
        executor: Executor(),
        view: self
    )
    
    func showForecast(forecast: Forecast) {
        label.text = forecast.base
        print(forecast)
    }
    
    func hideProgress() {
        
    }
    
    func showError(error: String) {
        
    }
    
    func showProgress() {
        
    }
    
    func showRetry(error: String, f: @escaping () -> Void) {
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!
}
