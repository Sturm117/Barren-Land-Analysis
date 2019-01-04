@echo off
title Barren Land Analysis Unit Testing
echo Begin Testing
echo *******************

REM -------------
REM Begin Testing
REM -------------

java BarrenLandAnalysis "" > ..\testing\test1.log
java BarrenLandAnalysis "{\" \"}" > ..\testing\test2.log
java BarrenLandAnalysis "{\"0 292 399 307\"}" > ..\testing\test3.log
java BarrenLandAnalysis "{\"0 0 399 599\"}" > ..\testing\test4.log
java BarrenLandAnalysis "{\"48 192 351 207\",\"48 392 351 407\",\"120 52 135 547\",\"260 52 275 547\"}" > ..\testing\test5.log
java BarrenLandAnalysis "{\"0 192 399 207\",\"0 392 399 407\",\"120 0 135 599\",\"260 0 275 599\"}" > ..\testing\test6.log

REM -------------------
REM Corner Cases
REM -------------------

java BarrenLandAnalysis "{\"0 0 0 0\"}" > ..\testing\Corner_test1.log
java BarrenLandAnalysis "{\"399 599 399 599\"}" > ..\testing\Corner_test2.log

REM -------------------
REM Invalid Coordinates
REM -------------------

java BarrenLandAnalysis "Bad Input" > ..\testing\Invalid_test1.log
java BarrenLandAnalysis "{\"0 0 500 800\"}" > ..\testing\Invalid_test2.log

REM ---------------
REM Compare Results
REM ---------------

FC ..\testing\test1.log.golden ..\testing\test1.log > ..\testing\results.log
FC ..\testing\test2.log.golden ..\testing\test2.log >> ..\testing\results.log
FC ..\testing\test3.log.golden ..\testing\test3.log >> ..\testing\results.log
FC ..\testing\test4.log.golden ..\testing\test4.log >> ..\testing\results.log
FC ..\testing\test5.log.golden ..\testing\test5.log >> ..\testing\results.log
FC ..\testing\test6.log.golden ..\testing\test6.log >> ..\testing\results.log
FC ..\testing\Invalid_test1.log.golden ..\testing\Invalid_test1.log >> ..\testing\results.log
FC ..\testing\Invalid_test2.log.golden ..\testing\Invalid_test2.log >> ..\testing\results.log
FC ..\testing\Corner_test1.log.golden ..\testing\Corner_test1.log >> ..\testing\results.log
FC ..\testing\Corner_test2.log.golden ..\testing\Corner_test2.log >> ..\testing\results.log