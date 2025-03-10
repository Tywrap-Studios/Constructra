---
date: 2025-03-10
categories:
    - Documentation Internals
authors:
  - tiazzz
---

# Have the tiazzz.me post here!

In [the last blog](new%20site.md) I told you to go look at my personal post somewhere else, but tbh that site is kind of shitty, so just have it here in a quote:

<!-- more -->

!!! quote "The Post"
    Today I took a quick gander at Material for MkDocs, and decided that THIS was gonna be the way I'm going to make my documentation sites from now on, starting with Constructra!

    Did it take me an entire day's worth of time?  
    YES  
    Was it definitely worth it in the end?  
    ALSO YES!!!!

    I started by just looking at this simple tutorial made by James Willett, many thanks go to him for creating a Written Guide For The Video that I was able to snoop some bits of code from, I highly suggest looking at his video(s) if you're also just getting started with MkDocs and want to host the site fully for free using GitHub Pages!
    - [The Video](https://www.youtube.com/watch?v=xlABhbnNrfI)
    - [The Guide](https://jameswillett.dev/getting-started-with-material-for-mkdocs/)

    ---
    Thanks to the many resources it provides, and to the fact it's an easy-to-use program, MkDocs has been a major help in me creating neat and fluid sites, <s>therefore we ignore that this site is busted and runs on some weird Jekyll software that I have no idea of on how it works.</s>

    What did I learn today? I'm a dimwit, I've been using Anaconda for ages to setup virtual environments for Python coding and apparently Python has that built in like what??  
    You can apparently run `python -m venv venv` and it'll create a fully new environment for yours to use! And you can even make use of it in your terminal by simply running some `activate` script found in the folders it creates. Wicked!!

    ---
    For those who are interested in looking at my new baby docs site, I made a docs hub at [docs.tiazzz.me](https://docs.tiazzz.me), it currently only contains links to Constructra's docs, of course, but when I do decide to add more documentations for my projects, it'll be of great use.

    Another reason as to why I made the hud, is so I can get proper DNS set up.  
    Because see, using custom domains with GitHub pages is a **pain in the ass**. If I want to make a docs site at, let's say, docs.tiazzz.me/site1, and run it in a repo, it'll be tywrap-studios.github.io/site1, now when I link the domain up to it, it'll actually just use the bare docs.tiazzz.me domain, meaning site1 is directly available on docs.tiazzz.me EVEN THOUGH THE CNAME RECORD I HAVE POINTS docs.tiazzz.me TO THE BARE tywrap-studios.github.io DOMAIN!!  
    Now luckily, making a Tywrap-Studios/Tywrap-Studios.github.io repo and putting a CNAME in *there* has fixed this, because docs.tiazzz.me now routes to that, which means that if I make a repo called Site2, its GitHub pages site would be at tywrap-studios.github.io/Site2, and now I can finally make docs.tiazzz.me/Site2 actually be that.

    Here's a small preview for those who don't trust my links:
    DOESN'T WORK ON MKDOCS CUZ YK

    Phew, what a job. Anyway, have a nice day! You can view the sites here (and go back if my post layout thing worked properly lol):
    - [Docs Hub](https://docs.tiazzz.me)
    - [WIP Constructra Docs](https://docs.tiazzz.me/Constructra)
    