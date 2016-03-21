# Peer to Peer to Peer Proximity Based Group Messaging (Jaw app)
## Cameron Knight
### UMKC CS449

***

## Revision History

|Author|Date|Revision|
|---|---|---|
|Knight|2016-02-05|Initial release|

***

## Table of Contents

- [Vision Statement](#vision-statement)
- [Requirements](#requirements)
    - [Step #1: Identify Categories of Users](#step-1-identify-categories-of-users)
    - [Step #2: Create Actor-Goal List](#step-2-create-actor-goal-list)
    - [Step #3: Identify User Stories](#step-3-identify-user-stories)
    - [Step #4: Write Use Cases](#vision-statement)
- [Iteration #1](#iteration-1)
- [Iteration #1](#iteration-2)
- [Design](#design)
- [Coding Standards](#coding-standards)
- [Project Summary and Retrospective](#project-summary-and-retrospective)

***

## Vision Statement

For the tech-savvy introvert who would rather have a text conversation than interact with a human in the real word **Jaw** bridges the gap. **Jaw** allows you to talk to anyone at a party, without all the social awkwardness (like eye contact). Unlike conventional messaging apps, **Jaw** only works with other **Jaw** users in your immediate proximity.

## Requirements

### Step #1: Identify Categories of Users

**Participant** - A user who actively sends and receives messages, or passively relays messages to other participants.

### Step #2: Create Actor-Goal List

|Actor|Goal|
|---|---|
|Participant|Send messages to all other engaged participants|
||*Potentially* see a list of all participants|
||Receive messages from all participant|
||*Potentially* send/receive direct messages from particular participants|
||Passively extend the reach of communication between participants|

### Step #3: Identify User Stories

|Story ID|Story                                                                                |Story Points|Priority|Status|
|:------:|-------------------------------------------------------------------------------------|:----------:|:------:|-----:|
|      S1|Participants will choose usernames to identify themselves.                           |           5|       1|      |
|      S2|Participants can also be identified by an avatar image.                              |           4|       2|      |
|      S3|Participant can enter a message.                                                     |           3|       3|      |
|      S4|Message should be sent to all other participants.                                    |           6|       4|      |
|      S5|Participant can see a history of past messages.                                      |           3|       5|      |
|      S6|Participant can see system messages.                                                 |           2|       6|      |
|      S7|System messages will show when participants join the conversation.                   |           2|       7|      |
|      S8|System messages should show when participants leave the conversation.                |           2|       8|      |
|      S9|Participants will receive messages from all other participants.                      |           4|       9|      |
|     S10|App will use Central to listen for advertisement from Peripherals.                   |           8|      10|      |
|     S11|App running on compatible devices will use Peripheral to advertise to other devices. |           7|      11|      |
|     S12|Central devices will connect to a Peripheral device to send and receive messages.    |          13|      12|      |
|     S13|A Peripheral device will connect to multiple Central devices.                        |           3|      13|      |
|     S14|A Peripheral device will send participant list to Central devices.                   |          15|      14|      |
|     S15|A Peripheral device will relay messages to Central devices.                          |          16|      15|      |
|     S16|As a participant, I would like to send direct messages to specific participants.     |           3|      16|      |
|     S17|The proximity space should increase as more participants join.                       |          18|      17|      |

### Step #4: Write Use Cases

---

**Title:** Join the conversation  
**Use case ID:** UC001  
**Actor:** Participant  
**Description:** This is the initial action all participants must take to join a conversation

**Basic Flow**

1. Upon app launch, other participants are automatically found and connected.
2. Participant can begin send messages to all other participants.
3. Other participant messages will appear to the participant.

**Alternative Flow**

1. No other participants are found.
    - The app show a message indicating no other participants.
    - The app waits for other participants to join.

**Exceptions**

1. Connection cannot be made with other participants.
    - Connection should automatically attempt to reconnect.

**Open issues**

1. How are participants identified?
    - Automatically generated ID?
    - User entered handle/username?
    - Avatar picture?

---

## Iteration #1

|Story ID|Task|Estimated Hours|Actual Hours|
|:-:|---|---|---|
|S1|Design UI|5|7|
||Develop model|8|5|
||Implement network layer|16||
|S2|Design message listener|8||
|S4|Implement send/receive/repeat message model|16||
||Use network topology to find fastest path between participants|16||

### Review Meeting

- UI was demoed to the client.
- Client didn't like the color scheme (it should be brighter).
- Generally the UI was well received.

### Retrospective

Instead of focusing on the networking, time was spent on implementing the UI layer. Also, Instead of designing UI mockups ahead of time design happened at the same time as implementation. This approach added some extra time because of the waisted time implementing UI elements that were later removed, but with my unfamiliarity with front-end Android code it helped guard against designing unimplementable elements.

I might need to retcon the story, since the Story Plan doesn't accurately represent the time spent during this iteration.

Project Velocity: 0

## Iteration #2

## Design

The networking design will use an iterative design to incrementally increase the range of communication. Initial design should focus on peer to peer communication (1 to 1). The network model should then focus on hub and spoke, allowing one node to act as the host and all others as clients. Finally, the model will act as a mesh network, with each node acting simultaneously as a client, host, and repeater.

## Coding Standards

Follow standard Java/Android coding standards. See <https://source.android.com/source/code-style.html>

## Project Summary and Retrospective
