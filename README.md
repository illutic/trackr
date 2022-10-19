# trackr - Another expense tracking app

Trying out Jetpack Compose by creating an expense tracking app. 

## Components that will be used

- Material2 Compose bottom sheets
- Material3 Compose components
- Dynamically sized Composables
- Material You
- Room

## Outcome/Result of App
This app is not finished, but so far the turn to Jetpack compose is not without its challenges. 
During the development of this project I have come to the conclusion that whilst Jetpack Compose significantly improves development time, it is not up to par with XML Views when it comes to performance and features. 
In my testing the debug version of the app had horrible performance, with frames dropping on any sort of list. The respective minified/obfuscated version ran a little better, but the user could clearly feel the jitter. Memory performance was also hit with almost 50% increase than XML views.
I also found the Bottom Sheet modals, a core Material UI component to be very unituitive to work with. In this project I had to wrap the whole app composable with the bottom sheet modals to achieve the same results as a BottomSheetDialogFragment.

More testing and refinement needs to be done, as the performance issues might be because of my lack of skills on Jetpack Compose. The lack of core material UI components is a clear disadvantage when developing a Jetpack Compose project over XML.
