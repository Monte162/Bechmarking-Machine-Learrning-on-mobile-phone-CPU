<?php


$firstline= `head -n1 trainsetcommand.txt`;
Echo $firstline;

$output=shell_exec($firstline);

echo $output;

?>