# Last FM Exercise

## Motivation

Portfolio Android Native Application that allows the user to search an artist from Last FM © (last.fm) and save its albums locally, along with their images.
This app makes use of patterns and technologies like:
- MVVM
- Room
- Paging
- Navigation Library
- Hilt (Dagger)
- Kotlin Flows
- Image Loading (with Coil)

## Build

The app is not runnable as-is, we will need to provide a Last FM API Key as a variable on the `local.properties` like so:

```groovy
LAST_FM_API_KEY=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

## Important Remarks

All the data, information, images and this app's logo were all obtained from whether Last FM's site (last.fm) or API.

For more information regarding this data and its usage, please consult Last FM's Terms of Service at
https://www.last.fm/api/tos


On the other hand, Last FM has disabled the Artist images on the API as discussed here: 
https://support.last.fm/t/cant-get-an-image-via-api/16571

On the app, I left the Artist image logic for demonstrative purposes but Artist images will only display a placeholder with a star.


Also, don't forget to check the app's Dark Mode out :)