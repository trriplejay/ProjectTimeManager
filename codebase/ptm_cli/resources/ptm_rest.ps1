$Process = [Diagnostics.Process]::Start("java","-jar ptm_rest-0.0.1-SNAPSHOT.jar")
$id = $Process.Id
Start-Sleep -Seconds 10
[Diagnostics.Process]::Start("java","-jar ptm_cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar").WaitForExit()
try {            
    Stop-Process -Id $id -ErrorAction stop            
} catch {            
}