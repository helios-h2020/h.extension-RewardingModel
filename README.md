# Overview

## Helios decentralized Rewarding and Rating Model

The main function of the decentralized reward system is to incentivize Helios users in the use of the platform. Users will be able to collect this incentive, in the form of a fungible token, and will be able to spend it for benefits within the platform (access to premium features, access to premium content, access to external services).

Why a decentralized (blockchain-based) reward system?
* An incentive system that is not dependent on a central issuer and has rules agreed by participants
* A censorship-free and transparent incentive system

The following figure shows the general scheme of the rewarding system:
* A user of the Helios network performs an action on the network that may be a candidate to be rewardable (according to system rules)
* The rewarding module manages the associated rules and incentives, confirms the rewardable action and applies the transfer of the corresponding tokens to the user's wallet
* The reward wallet accumulates such tokens as a balance of points that the user can use for other uses as if it were an internal currency of the system

![Diagram - general scheme](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/diagram1.png?raw=true)

As users contribute with valuable content or performing some of the identified rewardable actions on the network, they will earn Helios tokens rewards that will be directly linked to their relevance as a contributor on the Helios network. All rewards will initially transact via the Quorum Network through smart contracts.

A predetermined number of Helios tokens, based on the total daily activity directly linked with rewarding actions, will be issued at the end of each day as a Daily Reward Pool. An individual’s daily contribution will be determined by how much they contributed to the network that day relative to the entire community. This percentage will then be applied to the Daily Reward Pool to determine everyone’s daily token grants.

The following table outlines the scoring weight applied to each rewardable action to determine user contribution and relevance to the network:

![Table - Scores](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/table_scores.png?raw=true)

Hereafter, we present the main factors defined for the rewarding algorithms:

![Table - Algorithm factors](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/table_algorithm_factors.png?raw=true)

The total contribution of a user in the Helios network for a Reward Period (initially set to 1 day) is the following:

![Formula - Total contribution](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/formula1.png?raw=true)

The Relevance of this user is related to the whole contribution performed by the rest of the users in the network for a Reward Period:

![Formula - Relevance](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/formula2.png?raw=true)

The number of tokens available for distribution to contributors in each Rewarding Period (reward mining) is based on a pre-set calculation but it also depends on the number of active users contributing to the network:

![Formula - Total amount](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/formula3.png?raw=true) ![Formula - Total amount](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/formula3.5.png?raw=true)

Finally, the amount of Helios tokens that are transferred to each user’s wallet depend on the token production (reward mining) generated every Rewarding Period by Helios in the following way:

![Formula - HLO](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/formula4.png?raw=true)

## Utility of the Helios Token

Helios users will collect Helios rewarding tokens through the performance of activities and actions that are tagged as rewardable. These tokens will be collected in their mobile applications within a wallet module that will store the balance of tokens and the capability to transfer a partial or total amount in exchange of benefits accessible to each use case. We describe initial token utilities that will be defined within the scope of the different contexts and use cases where this token may be spent:
* Access to premium content
* Access to external services as a discount on the final price
* Content promotion
* Promotional campaigns for external services
* Platform development governance (voting on new features and product roadmap)

For more information you can download  D4.4 Rewarding Strategies.

## Wallet for HLO

There is a decentralized application of Helios in the Bloomen wallet:
* Bloomen wallet: https://play.google.com/store/apps/details?id=com.tempos21.bloomen&hl=es&gl=US
* Helios dApp QR:

![QR - Helios dApp](https://github.com/helios-h2020/h.extension-RewardingModel/blob/master/wiki-images/helios_qr.png?raw=true)

***

# Project structure
This project has two main components:
* An android SDK
* A sample Android app

The android SDK is oriented to help integrating the Reward Module to an existing Android app. The sample app helps to see a way to use the SDK on a Kotlin based app.

## Android SDK
There is a main class called RewardingSdk that have all the methods needed to use the Rewarding Module API. There are also some callbacks that needs to be implemented in order to use those methods.

## Sample Android app
This is a simple kotlin app that shows a way to use the SDK. It is made up of activities and presenters.

# Library usage

## Prerequisites
* The app must be able to provide a user identifier and a context or use case identifier.
* The minimum android version is 9 (Pie)
* The app must grant the `android.permission.INTERNET` permission.
## Integration
To use the Rewarding SDK, you need to copy the `.aar` file ![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/helios-h2020/h.extension-RewardingModel?include_prereleases&style=plastic) into your app's library directory. 
Then, you need to add the following dependency to your app's gradle build file:
```gradle
dependencies {
    implementation(name: 'helios-rewarding-sdk-debug', ext: 'aar')
}
```
Also, the app needs to apply some extra dependencies the SDK needs:  
```gradle
apply from: 'helios_rewarding_sdk.gradle'
```
This `helios_rewarding_sdk.gradle` is a file that needs to be placed into your app's gradle files.
The file contains the following code:  
```gradle
dependencies {
    def ktor_version = '1.3.1'
    def multiplatform_settings_version = "0.5"
    implementation "com.russhwolf:multiplatform-settings:$multiplatform_settings_version"
    implementation "io.ktor:ktor-client-android:$ktor_version"
    implementation "io.ktor:ktor-client-gson:$ktor_version"
    implementation "io.ktor:ktor-client-auth-jvm:$ktor_version"
    implementation "io.ktor:ktor-client-logging-jvm:$ktor_version"
}
```
## Usage
The Rewarding SDK has seven methods to interact with: 
```kotlin
interface RewardingSdk {
    fun registerUser(userID: String, heliosContext: String, callback: RegisterUserCallback)
    fun getToken(callback: GetTokenCallback)
    fun removeToken(callback: RemoveTokenCallback)
    fun recordRewardableActivity(rewardableActivities: List<RewardableActivity>, callback: RecordRewardableActivityCallback)
    fun cancel()
}
```
*  The `registerUser` method is the one that calls the `/auth/networkUser/register` Rewarding Module API method. It registers a user to the Rewarding Module using an identifyer an some context/use case identifyer. The response of the API method is an access token that the Rewarding SDK stores into the Sharedpreferences settings.
*  The `getToken` method reads the access token stored into the Sharedpreferences, if any.
*  The `removeToken` method removes the access token stored into the Sharedpreferences, if any.
*  The `recordRewardableActivity` method calls the `/activities/record` API method. It registers the activity of the user accepting a list of RewardableActivity objects.
*  The `cancel` method allows the app to stop any other running SDK process.  

Each one of this methods receive a callback object that needs to be implemented in order to manage the server response. Each callback is called by the `onSuccess` method when everything is OK and by the `onError` method when something go wrong.  
```kotlin
interface RegisterUserCallback {
    fun onError()
    fun onSuccess()
}
```
> The `registerUser` does not return any data, because it stores the access token automatically.
```kotlin
interface GetTokenCallback {
    fun onError()
    fun onSuccess(token: String)
}
```
> The `getToken` returns the access token as a String.
```kotlin
interface RemoveTokenCallback {
    fun onError()
    fun onSuccess()
}
```
> The `removeToken` does not return any data.
```kotlin
interface RecordRewardableActivityCallback {
    fun onError()
    fun onSuccess()
}
```
> The `recordRewardableActivity`does not return any data.

# Deepening

## Register user
| SDK method |
| ---------- |
| `com.wordline.helios.rewarding.sdk.RewardingSdk.registerUser` |

| API method |
| ---------- |
| `/hrm-api/auth/networkUser/register` |

| SDK callback |
| ------------ |
| `com.wordline.helios.rewarding.sdk.RegisterUserCallback` |

| Model |
| ----- |
| None |

This method registers a user to the Rewarding Module using an identifyer an some context/use case identifyer. The response of the API method is an access token that the Rewarding SDK stores into the Sharedpreferences settings.

### Example of use
```kotlin
    fun registerUser(userID: String, context: String) {
        rewardingSdk.registerUser(userID, context, object: RegisterUserCallback {
            override fun onError() {
                view.showError("error")
            }

            override fun onSuccess() {
                view.showSuccess()
            }
        })
    }
```

## Record activity
| SDK method |
| ---------- |
| `com.wordline.helios.rewarding.sdk.RewardingSdk.recordRewardableActivity` |

| API method |
| ---------- |
| `/hrm-api/activities/record` |

| SDK callback |
| ------------ |
| `com.wordline.helios.rewarding.sdk.RecordRewardableActivityCallback` |

| Model |
| ----- |
| `com.wordline.helios.rewarding.sdk.domain.model.RewardableActivity` |
| `com.wordline.helios.rewarding.sdk.domain.Action` |

This method registers the activity of the user accepting a list of RewardableActivity objects.

### Example of use
```kotlin
    fun registerActivity(actions: List<Action>, date: String) {
        val rewardableActivities: MutableList<RewardableActivity> = mutableListOf()
        for (action in actions) {
            rewardableActivities.add(RewardableActivity(action = action.value, date = date))
        }
        rewardingSdk.recordRewardableActivity(rewardableActivities, object: RecordRewardableActivityCallback {
            override fun onError() {
                view.showError("error")
            }
            override fun onSuccess() {
                view.showSuccess()
            }
        })
    }
```
The `List<Action>` named `actions` is a collection of enumerable values of the `enum Action`. The `RewardableActivity` has two attributes: `action` and `date`. So you need to send to the API a list of predefined actions, each one with a date related to it.  
The list of available actions:  
```kotlin
enum class Action(val value: String) {
    ACTIVE_AD_HOC_NETWORK("actions_active-ad-hoc-network"),
    BROADCASTING("actions_broadcasting"),
    CODING("actions_coding"),
    COMMUNITY_INCREASES("actions_community-increases"),
    CONNECTION_ACCEPTED("actions_connection-accepted"),
    CONTENT_SHARING("actions_content-sharing"),
    DATA_FEED("actions_data-feed"),
    EARLY_ADOPTERS("actions_early-adopters"),
    HELIOS_ACTIVATION("actions_helios-activation"),
    HELIOS_LEARNING("actions_helios-learning"),
    HELIOS_TRACKING("actions_helios-tracking"),
    MEDIA_ARCHIVE_UPLOADING("actions_media-archive-uploading"),
    MEET_UP_EVENT("actions_meet-up-event"),
    NEW_FEATURE("actions_new-feature"),
    PERSONAL_PROFILE("actions_personal-profile"),
    PLATFORM_FEEDBACK("actions_platform-feedback"),
    PUBLIC_CHAT_FORUM("actions_public-chat-forum")
}
```
