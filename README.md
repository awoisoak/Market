**Implementation details**

- Applies concepts of the [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [MVP](https://github.com/googlesamples/android-architecture) design pattern applied for the Presentation Layer
- Applies Dependency injection with  [Dagger2](https://google.github.io/dagger/)
- [ButterKnife](http://jakewharton.github.io/butterknife/) is used to find the corresponding views
- [Glide](https://github.com/bumptech/glide) is used for image processing
- [RxJava](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid) are used between the presenters and its interactor
- An RxBus pattern implemented with [RxRelay] (https://github.com/JakeWharton/RxRelay)(to avoid the Observable to finish if there is an error) to decouple Interactors/UseCases from Presenters



Two flavors were added: mock & prod (@see app/build.gradle) to provide a flexible enough implementation
Note that,  only the mock build is implemented as this app doesn't have access to Market backend
(a descriptive dialog is displayed if a prod build tries to be run)

MVP was applied to all fragments in parallel which, in this case as it is a simple test, some code is duplicated but it allows easy scalability if behaviours/style change in the future 

A responsive UI has been added for 7 inches tablets (600dp width)

![Screenshot phone](http://awoisoak.com/public/android/market_app_1.png)
![Screenshot tablet 7'](http://awoisoak.com/public/android/market_app_2.png)