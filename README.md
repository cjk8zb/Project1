# Peer to Peer to Peer Proximity Based Group Messaging
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
- [Design](#design)
- [Coding Standards](#coding-standards)
- [Project Summary and Retrospective](#project-summary-and-retrospective)

***

## Vision Statement

For the tech-savvy introvert who would rather have a text conversation than interact with a human in the real word **This App** bridges the gap. **This App** allows you to talk to anyone at a party, without all the social awkwardness (like eye contact). Unlike conventional messaging apps, **This App** only works with other **This App** users in your immediate proximity.

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

|Story ID|Story|Story Points|Priority|Status|
|:-:|---|:-:|:-:|--:|
|S1|Participant can send a message to all other participants|5|1||
|S2|Participants will receive messages from all other participants|3|2||
|S3|As a participant, I would like to send direct messages to specific participants|3|5||
|S4|The proximity space should increase as more participants join|10|3||

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
|S1|Design UI|5||
||Develop model|8||
||Implement network layer|16||
|S2|Design message listener|8||
|S4|Implement send/receive/repeat message model|16||
||Use network topology to find fastest path between participants|16||


## Design

The networking design will use an iterative design to incrementally increase the range of communication. Initial design should focus on peer to peer communication (1 to 1). The network model should then focus on hub and spoke, allowing one node to act as the host and all others as clients. Finally, the model will act as a mesh network, with each node acting simultaneously as a client, host, and repeater.

## Coding Standards

Follow standard Java/Android coding standards. See <https://source.android.com/source/code-style.html>

## Project Summary and Retrospective
