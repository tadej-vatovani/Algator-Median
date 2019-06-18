$n = 500000
$t = 0
For ($i=0; $i -lt 5; $i++) {
	
    For ($j=0; $j -lt 5; $j++) {
		Add-content test.txt "test${t}:${n}:RND"
		$t++
    }
	
	$n += 500000
}
