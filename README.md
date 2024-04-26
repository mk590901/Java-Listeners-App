# Java Listeners Sample

_Android application written on Java implements an alternative method of subscribing GUI elements to receive changes_.

## Introduction

* A little about listeners. Listeners are used to display data coming from external application components. Usually in __Android__, a fragment of an application subscribes to receive data from drivers of external devices connected to the phone via wifi or bluetooth, from a server on the cloud, and finally simply reflecting the state of the phone itself: battery charge level, Internet access, app permissions and much more.
Fragments aren't in generally some static application elements. They are created dynamically and deleted during the operation of the application. During fragment creation and it's subscribing to receive changes, need to think and about at what point the subscription should be terminated and the link to the fragment should be removed from the adapter responsible for updates. Otherwise, a lot of problems arise, including those leading to memory leaks.
* As a rule, a fragment subscribes to receive changes in the ***onAttached*** _callback_ and unsubscribes from updates adapter the ***onDetach*** _callback_, which coincides with the lifecycle of the fragment itself. In 99% of cases this approach is justified, but there are exceptions.
For example, ***if the fragment is located inside a pager fragment***, it isn't at all a fact that it will necessarily be closed when the parent fragment is deleted and will exit to ***onDetach*** _callback_. Most likely, the last fragment selected by tab or swipe will be closed, but closing process may not affect to the closing its neighbor page's fragment selected before. Perhaps this fragment will be closed later. For example, when ***closing an activity***, which is ***too late***.
* If open the pager a second time, then some of the pages will be registered again, and the adapter will include junk links.
Can, of course, solve the problem artificially: when closing the pager, forcefully unsubscribe all page fragments from the adapter, but this is somehow too troublesome.
* I found a simpler and, it seems to me, more reliable way to solve the problem. It's necessary to subscribe to receive messages not ***via fragments***, but ***GUI elements*** itself, i.e. elements derived from views. Need simply subscribe to ***onAttachedToWindow*** _callback_ and break the contract in _callback_ ***onDetachedFromWindow***.
* Moreover, this approach greatly simplifies updating views. There is no need to filter or assign changes inside fragment itself.
True, there is no free lunch and you have to pay for everything: instead of standard elements, need to use custom elements with overrided ***onAttachedToWindow*** and ***onDetachedFromWindow***.

## What need to pay attention to in the code

### UpdateAdapter class
* Contains a container of objects signed for receive changes
* Has registration and unsubscribe functions: ***registerListener*** and ***unregisterListener***.
* All signed elements must implement the ***IUpdate*** interface.
* Singleton ***UpdateAdapter*** creats in ***MainActivity*** class on start application in overrided function ***onCreate***: _UpdateAdapter.newInstance()_;

## Movie

https://github.com/mk590901/Java-Listeners-App/assets/125393245/57e2f0e8-422b-47b5-a6ff-7df0fd160a68



